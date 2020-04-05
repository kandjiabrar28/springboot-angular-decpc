import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { AnonymatComponent } from 'app/entities/anonymat/anonymat.component';
import { AnonymatService } from 'app/entities/anonymat/anonymat.service';
import { Anonymat } from 'app/shared/model/anonymat.model';

describe('Component Tests', () => {
  describe('Anonymat Management Component', () => {
    let comp: AnonymatComponent;
    let fixture: ComponentFixture<AnonymatComponent>;
    let service: AnonymatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [AnonymatComponent]
      })
        .overrideTemplate(AnonymatComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnonymatComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnonymatService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Anonymat(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.anonymats && comp.anonymats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
