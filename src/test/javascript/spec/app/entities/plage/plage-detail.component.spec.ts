import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PlageDetailComponent } from 'app/entities/plage/plage-detail.component';
import { Plage } from 'app/shared/model/plage.model';

describe('Component Tests', () => {
  describe('Plage Management Detail Component', () => {
    let comp: PlageDetailComponent;
    let fixture: ComponentFixture<PlageDetailComponent>;
    const route = ({ data: of({ plage: new Plage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
