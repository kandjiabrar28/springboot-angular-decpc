import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { ExamenComponent } from 'app/entities/examen/examen.component';
import { ExamenService } from 'app/entities/examen/examen.service';
import { Examen } from 'app/shared/model/examen.model';

describe('Component Tests', () => {
  describe('Examen Management Component', () => {
    let comp: ExamenComponent;
    let fixture: ComponentFixture<ExamenComponent>;
    let service: ExamenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExamenComponent]
      })
        .overrideTemplate(ExamenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Examen(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.examen && comp.examen[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
