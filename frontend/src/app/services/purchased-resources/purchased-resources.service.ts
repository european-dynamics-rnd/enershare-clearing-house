import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {environment} from "../../../environments/environment";
import {PurchasedResources} from "../../dtos/purchasedResources";

@Injectable({
  providedIn: 'root'
})
export class PurchasedResourcesService {


  constructor(private http: HttpClient) { }

  getSearchPurchasedResources(searchRequest: SearchRequest): Observable<Page<PurchasedResources>> {
    return this.http.post<Page<PurchasedResources>>(`${environment.serverUrl}/purchases/search`, searchRequest);
  }
}
