import { TestBed } from '@angular/core/testing';

import { WonAuctionService } from './won-auction.service';

describe('WinnedAuctionService', () => {
  let service: WonAuctionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WonAuctionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
