import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PlageComponent } from 'app/entities/plage/plage.component';
import { PlageService } from 'app/entities/plage/plage.service';
import { Plage } from 'app/shared/model/plage.model';

describe('Component Tests', () => {
  describe('Plage Management Component', () => {
    let comp: PlageComponent;
    let fixture: ComponentFixture<PlageComponent>;
    let service: PlageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageComponent]
      })
        .overrideTemplate(PlageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Plage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plages && comp.plages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
