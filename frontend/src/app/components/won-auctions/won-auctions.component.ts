import { Component, OnInit } from '@angular/core';
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {WonAuction} from "../../dtos/wonAuction";
import {WonAuctionService} from "../../services/won-auctions/won-auction.service";

@Component({
  selector: 'app-won-auction',
  templateUrl: './won-auctions.component.html',
  styleUrls: ['./won-auctions.component.css']
})
export class WonAuctionsComponent implements OnInit {

  filteredWonAuction: WonAuction[];
  collectionSize: number;
  page = 1;

  searchCriteriaList = new Array<SearchCriteria>();

  sortingApplied: boolean = false;

  filterDate: NgbDateStruct;

  searchRequest = new SearchRequest(0,10,'createdOn','desc',this.searchCriteriaList)

  filters = {
    resourceId: '',
    winnerBidHash: '',
    hash: '',
    endDate: '',
  };

  constructor(private http: HttpClient,
              private wonAuctionService: WonAuctionService) {}

  ngOnInit(): void {
    this.refreshWonAuction();
  }

  refreshWonAuction() {
    this.searchWonAuction();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshWonAuction();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshWonAuction();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['endDate'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['endDate'] = '';
    }
    this.searchWonAuction();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      delete this.filters[column];
    } else {
      this.filters[column] = value.toLowerCase();
    }
    this.searchWonAuction();
  }

  searchWonAuction() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.wonAuctionService.getWonAuction(this.searchRequest).subscribe(wonAuction => {
      this.filteredWonAuction = wonAuction.content;
      this.collectionSize = wonAuction.totalElements;
    })

  }
}
