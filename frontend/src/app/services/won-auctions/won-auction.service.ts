import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";

import {environment} from "../../../environments/environment";
import {WonAuction} from "../../dtos/wonAuction";

@Injectable({
  providedIn: 'root'
})
export class WonAuctionService {

  constructor(private http: HttpClient) { }

  getWonAuction(searchRequest: SearchRequest): Observable<Page<WonAuction>> {
    return this.http.post<Page<WonAuction>>(`${environment.serverUrl}/auctions/search-won`, searchRequest);
  }
}
