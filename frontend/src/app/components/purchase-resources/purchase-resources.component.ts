import { Component, OnInit } from '@angular/core';
import {SearchCriteria} from "../../dtos/searchCriteria";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {SearchRequest} from "../../dtos/searchRequest";
import {HttpClient} from "@angular/common/http";
import {PurchasedResources} from "../../dtos/purchasedResources";
import {PurchasedResourcesService} from "../../services/purchased-resources/purchased-resources.service";

@Component({
  selector: 'app-purchase-resources',
  templateUrl: './purchase-resources.component.html',
  styleUrls: ['./purchase-resources.component.css']
})
export class PurchaseResourcesComponent implements OnInit {

  filteredPurchasedResources: PurchasedResources[];
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
    hash: '',
  };

  constructor(private http: HttpClient,
              private purchasedResourcesService: PurchasedResourcesService) {}

  ngOnInit(): void {
    this.refreshPurchasedResources();
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


  refreshPurchasedResources() {
    this.searchPurchasedResources();
  }

  setPageSize(size: number) {
    this.searchRequest.pageSize = size;
    this.refreshPurchasedResources();
  }

  sortTable(column: string) {
    if (this.searchRequest?.sort === column) {
      this.searchRequest.direction = this.searchRequest?.direction === 'asc' ? 'desc' : 'asc';
    } else {
      this.searchRequest.sort = column;
      this.searchRequest.direction = 'asc';
    }
    this.sortingApplied = true;
    this.refreshPurchasedResources();
  }

  onDateFilterChange(value: NgbDateStruct) {
    if (value && typeof value === 'object' && 'year' in value && 'month' in value && 'day' in value) {
      this.filters['createdOn'] =  new Date(Date.UTC(value.year, value.month - 1, value.day)).toISOString();
    } else {
      this.filters['createdOn'] = '';
    }
    this.searchPurchasedResources();
  }

  onFilterChange(column: string, value: any) {
    if (!value) {
      delete this.filters[column];
    } else {
      this.filters[column] = column === 'price' ? parseFloat(value) : value.toLowerCase();
    }
    this.searchPurchasedResources();
  }

  searchPurchasedResources() {
    this.searchRequest.page = Math.max(this.page - 1, 0);

    this.searchCriteriaList = [];

    for (const column in this.filters) {
      if (this.filters[column]) {
        this.searchCriteriaList.push({ columnName: column, columnValue: this.filters[column] });
      }
    }

    this.searchRequest.searchCriteriaList = this.searchCriteriaList;

    this.purchasedResourcesService.getSearchPurchasedResources(this.searchRequest).subscribe(resource => {
      this.filteredPurchasedResources = resource.content;
      this.collectionSize = resource.totalElements;
    });

  }

}
