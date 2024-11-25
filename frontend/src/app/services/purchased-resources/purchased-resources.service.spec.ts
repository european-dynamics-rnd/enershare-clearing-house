import { TestBed } from '@angular/core/testing';

import { PurchasedResourcesService } from './purchased-resources.service';

describe('PurchasedResourcesService', () => {
  let service: PurchasedResourcesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PurchasedResourcesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
