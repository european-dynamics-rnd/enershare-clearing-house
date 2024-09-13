import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Logs} from "../../dtos/logs";
import {IngressLogsService} from "../../services/ingress-logs/ingress-logs.service";
import {NgbDatepicker, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchCriteria} from "../../dtos/searchCriteria";
import {SearchRequest} from "../../dtos/searchRequest";


@Component({
  selector: 'app-ingress-logs',
  templateUrl: './ingress-logs.component.html',
  styleUrls: ['./ingress-logs.component.css']
})
export class IngressLogsComponent implements OnInit {

  filteredLogs: Logs[];
  collectionSize: number;
  page = 1;

  searchCriteriaList = new Array<SearchCriteria>();

  sortingApplied: boolean = false;

  filterDate: NgbDateStruct;

  searchRequest = new SearchRequest(0,10,'createdOn','desc',this.searchCriteriaList)

  filters = {
    provider: '',
    resourceId: '',
    resourceType: '',
    contractId: '',
    createdOn: '',
    action: ''
  };

  constructor(private http: HttpClient,
              private ingressLogsService: IngressLogsService) {}

  ngOnInit(): void {
    this.refreshLogs();
  }

  refreshLogs() {
    this.searchLogs();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshLogs();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshLogs();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['createdOn'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['createdOn'] = '';
    }
    this.searchLogs();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      delete this.filters[column];
    } else {
      this.filters[column] = value.toLowerCase();
    }
    this.searchLogs();
  }

  searchLogs() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.ingressLogsService.getSearchLogs(this.searchRequest).subscribe(ingressLogs => {
      this.filteredLogs = ingressLogs.content;
      this.collectionSize = ingressLogs.totalElements;
    });

  }

  pad(n: number): string {
    return n < 10 ? '0' + n : n.toString();
  }


}
