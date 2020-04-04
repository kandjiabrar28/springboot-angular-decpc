import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TAnonymDetailComponent } from 'app/entities/t-anonym/t-anonym-detail.component';
import { TAnonym } from 'app/shared/model/t-anonym.model';

describe('Component Tests', () => {
  describe('TAnonym Management Detail Component', () => {
    let comp: TAnonymDetailComponent;
    let fixture: ComponentFixture<TAnonymDetailComponent>;
    const route = ({ data: of({ tAnonym: new TAnonym(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TAnonymDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TAnonymDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TAnonymDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tAnonym on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tAnonym).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
