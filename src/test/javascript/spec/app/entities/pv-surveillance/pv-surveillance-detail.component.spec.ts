import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PVSurveillanceDetailComponent } from 'app/entities/pv-surveillance/pv-surveillance-detail.component';
import { PVSurveillance } from 'app/shared/model/pv-surveillance.model';

describe('Component Tests', () => {
  describe('PVSurveillance Management Detail Component', () => {
    let comp: PVSurveillanceDetailComponent;
    let fixture: ComponentFixture<PVSurveillanceDetailComponent>;
    const route = ({ data: of({ pVSurveillance: new PVSurveillance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PVSurveillanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PVSurveillanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PVSurveillanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pVSurveillance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pVSurveillance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
