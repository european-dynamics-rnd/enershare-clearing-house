import { Injectable } from '@angular/core';
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ProposedAuction} from "../../dtos/proposedAuction";

@Injectable({
  providedIn: 'root'
})
export class ProposedAuctionService {

  constructor(private http: HttpClient) { }

  getProposedAuction(searchRequest: SearchRequest): Observable<Page<ProposedAuction>> {
    return this.http.post<Page<ProposedAuction>>(`${environment.serverUrl}/auctions/search-proposed`, searchRequest);
  }

}
