import { Component } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator} from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bond',
  standalone: true,
  imports: [MatTableModule,MatSortModule,MatPaginatorModule,MatFormFieldModule,MatInputModule,FormsModule,CommonModule],
  templateUrl: './bond.component.html',
  styleUrl: './bond.component.css'
})
export class BondComponent {

bond_cal_responce: any;
float_bond_cal_responce:any;

getUserData(arg0: any) {
throw new Error('Method not implemented.');
}
  


  constructor( private http:HttpClient ){}
  get_bond_data(bond:any){
       
        
     return  this.http.post("http://localhost:9191/fi/bond_val",bond).subscribe(responce=>{
        this.bond_cal_responce=responce;
  })}

  get_float_bond_data(float_bond:any){
       
    console.log(float_bond);
    
      return this.http.post("http://localhost:9191/fi/float_bond_val",float_bond).subscribe(responce=>{
       this.float_bond_cal_responce=responce;
       console.log(responce);
       
        
      })
  }

}
