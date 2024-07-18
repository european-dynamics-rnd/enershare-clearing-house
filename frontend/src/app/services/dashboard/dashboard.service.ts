import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {LogSummary} from "../../dtos/logSummary";
import {Logs} from "../../dtos/logs";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  getSummary(): Observable<LogSummary[]> {
    return this.http.get<LogSummary[]>(`${environment.serverUrl}/logs/summary`);
  }

  getLastTenHoursSummary(): Observable<LogSummary[]> {
    return this.http.get<LogSummary[]>(`${environment.serverUrl}/logs/summaryHours`);
  }

  getLogs(logType: 'ingress' | 'egress'): Observable<any> {
    return this.http.get<any>(`${environment.serverUrl}/logs/${logType}`);
  }

  getLatestIngressLogs(count: number = 5): Observable<Logs[]> {
    return this.http.get<Logs[]>(`${environment.serverUrl}/logs/latestIngressLogs?count=${count}`);
  }

  getLatestEgressLogs(count: number = 5): Observable<Logs[]> {
    return this.http.get<Logs[]>(`${environment.serverUrl}/logs/latestEgressLogs?count=${count}`);
  }

}
