import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { SpecialiteComponent } from 'app/entities/specialite/specialite.component';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
import { Specialite } from 'app/shared/model/specialite.model';

describe('Component Tests', () => {
  describe('Specialite Management Component', () => {
    let comp: SpecialiteComponent;
    let fixture: ComponentFixture<SpecialiteComponent>;
    let service: SpecialiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SpecialiteComponent]
      })
        .overrideTemplate(SpecialiteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialiteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialiteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Specialite(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.specialites && comp.specialites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
