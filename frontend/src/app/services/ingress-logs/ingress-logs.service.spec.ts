import { TestBed } from '@angular/core/testing';

import { IngressLogsService } from './ingress-logs.service';

describe('IngressLogsService', () => {
  let service: IngressLogsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IngressLogsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
