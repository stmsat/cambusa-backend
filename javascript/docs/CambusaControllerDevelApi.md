# CambusaController.CambusaControllerDevelApi

All URIs are relative to *http://127.0.0.1:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**init**](CambusaControllerDevelApi.md#init) | **GET** /cambusaDevel/init | 

<a name="init"></a>
# **init**
> &#x27;String&#x27; init()



Inizializza il dataset di test

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerDevelApi();
apiInstance.init((error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

