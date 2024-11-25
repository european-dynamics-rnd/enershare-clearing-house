import { TestBed } from '@angular/core/testing';

import { BidsAcceptedService } from './bids-accepted.service';

describe('BidsAcceptedService', () => {
  let service: BidsAcceptedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BidsAcceptedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
