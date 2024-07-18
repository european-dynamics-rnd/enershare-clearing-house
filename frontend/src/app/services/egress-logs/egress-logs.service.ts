import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Page} from "../../dtos/page";
import {Logs} from "../../dtos/logs";
import {SearchRequest} from "../../dtos/searchRequest";

@Injectable({
  providedIn: 'root'
})
export class EgressLogsService {

  constructor(private http: HttpClient) { }

  getSearchLogs(searchRequest: SearchRequest): Observable<Page<Logs>> {
    return this.http.post<Page<Logs>>(`${environment.serverUrl}/logs/egress`, searchRequest);
  }

}
