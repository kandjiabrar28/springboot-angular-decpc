import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';
import { ICorrecteur, Correcteur } from 'app/shared/model/correcteur.model';

describe('Service Tests', () => {
  describe('Correcteur Service', () => {
    let injector: TestBed;
    let service: CorrecteurService;
    let httpMock: HttpTestingController;
    let elemDefault: ICorrecteur;
    let expectedResult: ICorrecteur | ICorrecteur[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CorrecteurService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Correcteur(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Correcteur', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.create(new Correcteur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Correcteur', () => {
        const returnedFromService = Object.assign(
          {
            grade: 'BBBBBB',
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Correcteur', () => {
        const returnedFromService = Object.assign(
          {
            grade: 'BBBBBB',
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Correcteur', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
