import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PVSurveillanceComponent } from 'app/entities/pv-surveillance/pv-surveillance.component';
import { PVSurveillanceService } from 'app/entities/pv-surveillance/pv-surveillance.service';
import { PVSurveillance } from 'app/shared/model/pv-surveillance.model';

describe('Component Tests', () => {
  describe('PVSurveillance Management Component', () => {
    let comp: PVSurveillanceComponent;
    let fixture: ComponentFixture<PVSurveillanceComponent>;
    let service: PVSurveillanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PVSurveillanceComponent]
      })
        .overrideTemplate(PVSurveillanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PVSurveillanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PVSurveillanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PVSurveillance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pVSurveillances && comp.pVSurveillances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
