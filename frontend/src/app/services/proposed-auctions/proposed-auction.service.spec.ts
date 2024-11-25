import { TestBed } from '@angular/core/testing';

import { ProposedAuctionService } from './proposed-auction.service';

describe('ProposedAuctionService', () => {
  let service: ProposedAuctionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProposedAuctionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
