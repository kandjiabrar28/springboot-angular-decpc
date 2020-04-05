import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { AnonymatDetailComponent } from 'app/entities/anonymat/anonymat-detail.component';
import { Anonymat } from 'app/shared/model/anonymat.model';

describe('Component Tests', () => {
  describe('Anonymat Management Detail Component', () => {
    let comp: AnonymatDetailComponent;
    let fixture: ComponentFixture<AnonymatDetailComponent>;
    const route = ({ data: of({ anonymat: new Anonymat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [AnonymatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnonymatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnonymatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load anonymat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anonymat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
