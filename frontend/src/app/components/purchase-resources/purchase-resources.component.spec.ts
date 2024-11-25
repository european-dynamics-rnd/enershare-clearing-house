import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseResourcesComponent } from './purchase-resources.component';

describe('PurchaseResourcesComponent', () => {
  let component: PurchaseResourcesComponent;
  let fixture: ComponentFixture<PurchaseResourcesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseResourcesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseResourcesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
