import { Injectable } from '@angular/core';
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {BidsAccepted} from "../../dtos/bids-accepted";
import {environment} from "../../../environments/environment";
import {BidsPlaced} from "../../dtos/bids-placed";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class BidsPlacedService {

  constructor(private http: HttpClient) { }

  getBidsPlaced(searchRequest: SearchRequest): Observable<Page<BidsPlaced>> {
    return this.http.post<Page<BidsPlaced>>(`${environment.serverUrl}/bids/placed/search`, searchRequest);
  }
}
