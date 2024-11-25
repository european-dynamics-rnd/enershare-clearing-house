import { Injectable } from '@angular/core';
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {ProposedAuction} from "../../dtos/proposedAuction";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {BidsAccepted} from "../../dtos/bids-accepted";

@Injectable({
  providedIn: 'root'
})
export class BidsAcceptedService {

  constructor(private http: HttpClient) { }

  getBidsAccepted(searchRequest: SearchRequest): Observable<Page<BidsAccepted>> {
    return this.http.post<Page<BidsAccepted>>(`${environment.serverUrl}/bids/accepted/search`, searchRequest);
  }
}
