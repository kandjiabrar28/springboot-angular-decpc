import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { TourComponent } from 'app/entities/tour/tour.component';
import { TourService } from 'app/entities/tour/tour.service';
import { Tour } from 'app/shared/model/tour.model';

describe('Component Tests', () => {
  describe('Tour Management Component', () => {
    let comp: TourComponent;
    let fixture: ComponentFixture<TourComponent>;
    let service: TourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TourComponent]
      })
        .overrideTemplate(TourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tour(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tours && comp.tours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
