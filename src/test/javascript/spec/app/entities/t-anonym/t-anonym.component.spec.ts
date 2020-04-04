import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { TAnonymComponent } from 'app/entities/t-anonym/t-anonym.component';
import { TAnonymService } from 'app/entities/t-anonym/t-anonym.service';
import { TAnonym } from 'app/shared/model/t-anonym.model';

describe('Component Tests', () => {
  describe('TAnonym Management Component', () => {
    let comp: TAnonymComponent;
    let fixture: ComponentFixture<TAnonymComponent>;
    let service: TAnonymService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TAnonymComponent]
      })
        .overrideTemplate(TAnonymComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TAnonymComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TAnonymService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TAnonym(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tAnonyms && comp.tAnonyms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
