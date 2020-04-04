import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { SurveillantDetailComponent } from 'app/entities/surveillant/surveillant-detail.component';
import { Surveillant } from 'app/shared/model/surveillant.model';

describe('Component Tests', () => {
  describe('Surveillant Management Detail Component', () => {
    let comp: SurveillantDetailComponent;
    let fixture: ComponentFixture<SurveillantDetailComponent>;
    const route = ({ data: of({ surveillant: new Surveillant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SurveillantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SurveillantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SurveillantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load surveillant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.surveillant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
