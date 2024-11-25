import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {LogSummary} from "../../dtos/logSummary";
import {Logs} from "../../dtos/logs";
import {PurchaseSummary} from "../../dtos/purchaseSummary";
import {Amount} from "../../dtos/amount";
import {PurchasedResources} from "../../dtos/purchasedResources";

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

  getLatestIngressLogs(count: number = 5): Observable<Logs[]> {
    return this.http.get<Logs[]>(`${environment.serverUrl}/logs/latestIngressLogs?count=${count}`);
  }

  getLatestEgressLogs(count: number = 5): Observable<Logs[]> {
    return this.http.get<Logs[]>(`${environment.serverUrl}/logs/latestEgressLogs?count=${count}`);
  }

  getLatestProvidedPurchasedRecourses(count: number = 5): Observable<PurchasedResources[]> {
    return this.http.get<PurchasedResources[]>(`${environment.serverUrl}/purchases/latest-purchased?count=${count}`);
  }

  getLatestConsumedPurchasedRecourses(count: number = 5): Observable<PurchasedResources[]> {
    return this.http.get<PurchasedResources[]>(`${environment.serverUrl}/purchases/latest?count=${count}`);
  }

  getIncomes(): Observable<Amount[]> {
    return this.http.get<Amount[]>(`${environment.serverUrl}/purchases/incomes`);
  }

  getExpenses(): Observable<Amount[]> {
    return this.http.get<Amount[]>(`${environment.serverUrl}/purchases/expenses`);
  }

}
