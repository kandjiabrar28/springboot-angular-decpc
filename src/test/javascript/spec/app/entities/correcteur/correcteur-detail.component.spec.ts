import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { CorrecteurDetailComponent } from 'app/entities/correcteur/correcteur-detail.component';
import { Correcteur } from 'app/shared/model/correcteur.model';

describe('Component Tests', () => {
  describe('Correcteur Management Detail Component', () => {
    let comp: CorrecteurDetailComponent;
    let fixture: ComponentFixture<CorrecteurDetailComponent>;
    const route = ({ data: of({ correcteur: new Correcteur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [CorrecteurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CorrecteurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CorrecteurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load correcteur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.correcteur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
