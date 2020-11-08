import { elasticsearchReindexRoute } from './elasticsearch-reindex/elasticsearch-reindex.route';
import { Routes } from '@angular/router';

import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, metricsRoute, userMgmtRoute } from './';

import { UserRouteAccessService } from 'app/core';

const ADMIN_ROUTES = [
  auditsRoute,
  configurationRoute,
  docsRoute,
  healthRoute,
  logsRoute,
  ...userMgmtRoute,
  metricsRoute,
  elasticsearchReindexRoute
];

export const adminState: Routes = [
  {
    path: '',
    data: {
      authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
  }
];
