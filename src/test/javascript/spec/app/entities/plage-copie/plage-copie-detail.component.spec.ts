import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PlageCopieDetailComponent } from 'app/entities/plage-copie/plage-copie-detail.component';
import { PlageCopie } from 'app/shared/model/plage-copie.model';

describe('Component Tests', () => {
  describe('PlageCopie Management Detail Component', () => {
    let comp: PlageCopieDetailComponent;
    let fixture: ComponentFixture<PlageCopieDetailComponent>;
    const route = ({ data: of({ plageCopie: new PlageCopie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageCopieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlageCopieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlageCopieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plageCopie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plageCopie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
