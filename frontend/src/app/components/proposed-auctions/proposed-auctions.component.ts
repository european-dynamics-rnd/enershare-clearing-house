import { Component, OnInit } from '@angular/core';
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {ProposedAuction} from "../../dtos/proposedAuction";
import {ProposedAuctionService} from "../../services/proposed-auctions/proposed-auction.service";

@Component({
  selector: 'app-proposed-auctions',
  templateUrl: './proposed-auctions.component.html',
  styleUrls: ['./proposed-auctions.component.css']
})
export class ProposedAuctionsComponent implements OnInit {

  filteredProposedAuction: ProposedAuction[];
  collectionSize: number;
  page = 1;

  searchCriteriaList = new Array<SearchCriteria>();

  sortingApplied: boolean = false;

  createdOnFilter: NgbDateStruct;
  endDateFilter: NgbDateStruct;

  searchRequest = new SearchRequest(0,10,'createdOn','desc',this.searchCriteriaList)

  filters = {
    resourceId: '',
    hash: '',
    status: '',
    createdOn: '',
    endDate: '',
  };

  constructor(private http: HttpClient,
              private proposedAuctionService: ProposedAuctionService) {}

  ngOnInit(): void {
    this.refreshProposedAuction();
  }

  refreshProposedAuction() {
    this.searchProposedAuction();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshProposedAuction();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshProposedAuction();
  }

  onDateFilterChange(column: string, value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters[column] = new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters[column] = '';
    }
    this.searchProposedAuction();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {

      delete this.filters[column];
    } else {
      if (column === 'status') {

        this.filters[column] =
          value === 'Open Auctions' ? 'AuctionOpened' :
            value === 'Closed Auctions' ? 'AuctionClosed' : value;
      } else {
        this.filters[column] = value.toLowerCase();
      }
    }
    this.searchProposedAuction();
  }

  searchProposedAuction() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.proposedAuctionService.getProposedAuction(this.searchRequest).subscribe(proposedAuction => {
      this.filteredProposedAuction = proposedAuction.content;
      this.collectionSize = proposedAuction.totalElements;
    });

  }


}
