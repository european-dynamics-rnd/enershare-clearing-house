import { TestBed } from '@angular/core/testing';

import { EgressLogsService } from './egress-logs.service';

describe('EgressLogsService', () => {
  let service: EgressLogsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EgressLogsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
