import { Component, OnInit } from '@angular/core';
import {Logs} from "../../dtos/logs";
import {Resources} from "../../dtos/resources";
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {EgressLogsService} from "../../services/egress-logs/egress-logs.service";
import {BidsAccepted} from "../../dtos/bids-accepted";
import {BidsAcceptedService} from "../../services/bids-accepted/bids-accepted.service";

@Component({
  selector: 'app-bids-accepted',
  templateUrl: './bids-accepted.component.html',
  styleUrls: ['./bids-accepted.component.css']
})
export class BidsAcceptedComponent implements OnInit {

  filteredBidsAccepted: BidsAccepted[];
  collectionSize: number;
  page = 1;

  searchCriteriaList = new Array<SearchCriteria>();

  sortingApplied: boolean = false;

  filterDate: NgbDateStruct;

  searchRequest = new SearchRequest(0,10,'createdOn','desc',this.searchCriteriaList)

  filters = {
    resourceId: '',
    hash: '',
    auctionHash: '',
    createdOn: '',
  };

  constructor(private http: HttpClient,
              private bidsAcceptedService: BidsAcceptedService) {}

  ngOnInit(): void {
    this.refreshBidsAccepted();
  }

  refreshBidsAccepted() {
    this.searchBidsAccepted();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshBidsAccepted();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshBidsAccepted();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['createdOn'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['createdOn'] = '';
    }
    this.searchBidsAccepted();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      delete this.filters[column];
    } else {
      this.filters[column] = value.toLowerCase();
    }
    this.searchBidsAccepted();
  }

  searchBidsAccepted() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.bidsAcceptedService.getBidsAccepted(this.searchRequest).subscribe(bidsAccepted => {
      this.filteredBidsAccepted = bidsAccepted.content;
      this.collectionSize = bidsAccepted.totalElements;
    });

  }

}
