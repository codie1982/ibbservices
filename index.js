import { NativeModules,Platform, Alert } from 'react-native';
//import moduleName from 'react-native-permissions'
const { IbbMobileServices } = NativeModules;

class IBBServices {

  async  configure(application_id){
        if(Platform.OS =="android"){
            //yok burada geri dönüş almamız gerekiyor buradaki geri dönüşe göre bir alert gösterecez ve bu alert içinden yükleme yapılmasını istememiz gerekiyor
          
            //Uygulama ilk başlangıç değerlerini alıyoruz. burada uygulamamıza ait device id,ve applicasyon özellikleri di db den okuyoruz
            const data = await IbbMobileServices.init(application_id)

            //Uygulamanın mevcut verilerini alıyoruz. burada versiyon kontrolu için de parametre geri döndürüyoruz.
            
            const current_data = await IbbMobileServices.current_data(data.deviceID,data.package_name,data.published_version)  

          
              IbbMobileServices.check_version(current_data.current_version,data.published_version,
                (result)=>{

            if(result){
                console.log("Version Güncellemesi gerekli")
                Alert.alert("Uygulama Güncellemesi gerekli",
                "Uygulamanıza ait yeni bir versiyon bulunmaktadır.Uygulamanızı dilerseniz hemen güncelleyebilirsiniz.",
                [
                    {
                      text: "Hayır",
                      onPress: () => 
                      {
                          //Uygulamayı güncelle uyarısını devre dışı bırak 
                          //Uygulama güncellemek için store uygulamasını kullan
                            console.log("uygulamayı daha sonra güncelle")
                      },
                      style: "cancel"
                    },
                    { text: "Güncelle", 
                    onPress: async () => 
                        {
                          const permission = await IbbMobileServices.check_permission();
                          if(permission){
                            const update = await IbbMobileServices.update_application(data.download_link,
                                                                                        data.application_name,
                                                                                        data.deviceID,
                                                                                        data.published_version,
                                                                                        data.package_name);
                          }else {
                            Alert.alert("Uygulama İzni Hatası",
                            "Bu uygulamada gerekli olan güncellemeler için herhangi bir izin verilmemiş.Uygulamaya gerekli izinlerin verilmesi için ayarlar bölümünden uygulamaya gerekli izinleri manuel olarak verebilirsiniz.",
                            [
                              {
                                text: "Tamam",
                                onPress: () => 
                                {
                                    //Uygulamayı güncelle uyarısını devre dışı bırak 
                                    //Uygulama güncellemek için store uygulamasını kullan
                                      console.log("uygulamayı daha sonra güncelle")
                                },
                                style: "OK"
                              }
                            ],
                            { cancelable: true }
                            )
                          }
                          
                        }
                },
                    { text: "Sonra Hatırlat", 
                    onPress:  () => 
                        {
                            //Uygulamayı birdahaki açılışta güncelle
                            console.log("Uygulamamı Sonra Güncelle")
                        } 
                }
                  ],
                    { cancelable: true }
                  )
            }else {
                console.log("Uygulama versiyonu güncel")
            }
           })
           
        }
    }

   async check_per(){
      return permission;
    }
}

const ibbservices = new IBBServices;
export default ibbservices;
