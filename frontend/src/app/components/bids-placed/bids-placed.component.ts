import { Component, OnInit } from '@angular/core';
import {Logs} from "../../dtos/logs";
import {Resources} from "../../dtos/resources";
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {BidsPlaced} from "../../dtos/bids-placed";
import {BidsPlacedService} from "../../services/bids-placed/bids-placed.service";

@Component({
  selector: 'app-bids',
  templateUrl: './bids-placed.component.html',
  styleUrls: ['./bids-placed.component.css']
})
export class BidsPlacedComponent implements OnInit {

  filteredBidsPlaced: BidsPlaced[];
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
              private bidsPlacedService: BidsPlacedService) {}

  ngOnInit(): void {
    this.refreshBidsPlaced();
  }

  refreshBidsPlaced() {
    this.searchBidsPlaced();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshBidsPlaced();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshBidsPlaced();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['createdOn'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['createdOn'] = '';
    }
    this.searchBidsPlaced();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      delete this.filters[column];
    } else {
      this.filters[column] = value.toLowerCase();
    }
    this.searchBidsPlaced();
  }

  searchBidsPlaced() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.bidsPlacedService.getBidsPlaced(this.searchRequest).subscribe(bidsPlaced => {
      this.filteredBidsPlaced = bidsPlaced.content;
      this.collectionSize = bidsPlaced.totalElements;
    });

  }

}
