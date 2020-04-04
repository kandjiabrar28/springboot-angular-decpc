import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { JuryComponent } from 'app/entities/jury/jury.component';
import { JuryService } from 'app/entities/jury/jury.service';
import { Jury } from 'app/shared/model/jury.model';

describe('Component Tests', () => {
  describe('Jury Management Component', () => {
    let comp: JuryComponent;
    let fixture: ComponentFixture<JuryComponent>;
    let service: JuryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [JuryComponent]
      })
        .overrideTemplate(JuryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JuryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JuryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Jury(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.juries && comp.juries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
