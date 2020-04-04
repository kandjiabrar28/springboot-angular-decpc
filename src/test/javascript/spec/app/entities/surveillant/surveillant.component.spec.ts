import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { SurveillantComponent } from 'app/entities/surveillant/surveillant.component';
import { SurveillantService } from 'app/entities/surveillant/surveillant.service';
import { Surveillant } from 'app/shared/model/surveillant.model';

describe('Component Tests', () => {
  describe('Surveillant Management Component', () => {
    let comp: SurveillantComponent;
    let fixture: ComponentFixture<SurveillantComponent>;
    let service: SurveillantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SurveillantComponent]
      })
        .overrideTemplate(SurveillantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveillantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveillantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Surveillant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.surveillants && comp.surveillants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
