import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PlageCopieComponent } from 'app/entities/plage-copie/plage-copie.component';
import { PlageCopieService } from 'app/entities/plage-copie/plage-copie.service';
import { PlageCopie } from 'app/shared/model/plage-copie.model';

describe('Component Tests', () => {
  describe('PlageCopie Management Component', () => {
    let comp: PlageCopieComponent;
    let fixture: ComponentFixture<PlageCopieComponent>;
    let service: PlageCopieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageCopieComponent]
      })
        .overrideTemplate(PlageCopieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlageCopieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlageCopieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlageCopie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plageCopies && comp.plageCopies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
