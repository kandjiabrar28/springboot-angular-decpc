import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { CorrecteurComponent } from 'app/entities/correcteur/correcteur.component';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';
import { Correcteur } from 'app/shared/model/correcteur.model';

describe('Component Tests', () => {
  describe('Correcteur Management Component', () => {
    let comp: CorrecteurComponent;
    let fixture: ComponentFixture<CorrecteurComponent>;
    let service: CorrecteurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [CorrecteurComponent]
      })
        .overrideTemplate(CorrecteurComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorrecteurComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CorrecteurService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Correcteur(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.correcteurs && comp.correcteurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
