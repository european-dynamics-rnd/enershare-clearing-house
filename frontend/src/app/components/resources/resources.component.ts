import { Component, OnInit } from '@angular/core';
import {Logs} from "../../dtos/logs";
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {EgressLogsService} from "../../services/egress-logs/egress-logs.service";
import {Resources} from "../../dtos/resources";
import {ResourceService} from "../../services/resource/resource.service";
import {Resource} from "@angular/compiler-cli/src/ngtsc/metadata";

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  filteredResources: Resources[];
  collectionSize: number;
  page = 1;

  searchCriteriaList = new Array<SearchCriteria>();

  sortingApplied: boolean = false;

  filterDate: NgbDateStruct;

  searchRequest = new SearchRequest(0,10,'createdOn','desc',this.searchCriteriaList)

  filters = {
    resourceId: '',
    status: '',
    price: '',
    createdOn: '',
    hash: ''
  };

  constructor(private http: HttpClient,
              private resourceService : ResourceService) {}

  ngOnInit(): void {
    this.refreshResources();
   // this.loadFreeResources();
  }

    // loadFreeResources() {
    //     this.resourceService.getFreeResources().subscribe({
    //         next: (resources) => {
    //             this.filteredResources = resources;
    //             console.log('Filtered Resources:', this.filteredResources);  // Log the data to ensure it's populated
    //         },
    //         error: (err) => console.error('Error fetching free resources', err)
    //     });
    // }


  refreshResources() {
    this.searchResources();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshResources();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshResources();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['createdOn'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['createdOn'] = '';
    }
    this.searchResources();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      // Remove the filter if no value is selected
      delete this.filters[column];
    } else {
      // Map 'On Sale' to 'onSale' for the back-end
      if (column === 'status' && value === 'On Sale') {
        this.filters[column] = 'onSale';
      } else {
        this.filters[column] = column === 'price' ? parseFloat(value) : value;
      }
    }

    // Trigger the search with updated filters
    this.searchResources();
  }

  searchResources() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.resourceService.getSearchResources(this.searchRequest).subscribe(resource => {
      this.filteredResources = resource.content;
      this.collectionSize = resource.totalElements;
    });

  }


}
