import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskAnaComponent } from './risk-ana.component';

describe('RiskAnaComponent', () => {
  let component: RiskAnaComponent;
  let fixture: ComponentFixture<RiskAnaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskAnaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RiskAnaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
