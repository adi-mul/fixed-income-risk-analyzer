import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { BondComponent } from './bond/bond.component';
import { RiskComponent } from './risk/risk.component';
import { RiskAnaComponent } from './risk-ana/risk-ana.component';

export const routes: Routes = [

    {path:'', component:HomeComponent},
    {path:'bond_cal',component:BondComponent},
    {path:'risk',component:RiskComponent},
    {path:'analysis',component:RiskAnaComponent}
];
