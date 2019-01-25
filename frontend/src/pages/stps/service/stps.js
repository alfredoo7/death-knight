import request from '../../../utils/request';

export function index (){
    return request(`/bapi/index`);
}