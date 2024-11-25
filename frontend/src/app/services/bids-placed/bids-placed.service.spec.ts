import { TestBed } from '@angular/core/testing';

import { BidsPlacedService } from './bids-placed.service';

describe('BidsPLacedService', () => {
  let service: BidsPlacedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BidsPlacedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
