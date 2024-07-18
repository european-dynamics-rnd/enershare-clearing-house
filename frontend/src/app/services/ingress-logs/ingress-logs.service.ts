import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Logs} from "../../dtos/logs";
import {SearchCriteria} from "../../dtos/searchCriteria";
import {SearchRequest} from "../../dtos/searchRequest";

@Injectable({
  providedIn: 'root'
})
export class IngressLogsService {

  constructor(private http: HttpClient) { }

  getSearchLogs(searchRequest: SearchRequest): Observable<Page<Logs>> {
    return this.http.post<Page<Logs>>(`${environment.serverUrl}/logs/ingress`, searchRequest);
  }

}
