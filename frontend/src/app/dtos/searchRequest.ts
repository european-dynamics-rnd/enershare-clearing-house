import {SearchCriteria} from "./searchCriteria";
import {PageRequest} from "./pageRequest";

export class SearchRequest  {
  constructor(page:number,pageSize:number, sort: string, direction: string, searchCriteriaList: SearchCriteria[]) {
    this.page = page;
    this.pageSize = pageSize
    this.sort = sort;
    this.direction = direction;
    this.searchCriteriaList = searchCriteriaList;
  }

  page:number;
  pageSize:number;
  sort: string;
  direction: string;
  searchCriteriaList: SearchCriteria[];

}
