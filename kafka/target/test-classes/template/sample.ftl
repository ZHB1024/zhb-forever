<html>    
<head>    
  <title>${title}</title>    
  <style>    
     table {     
             width:100%;border:green dotted ;border-width:2 0 0 2     
     }     
     td {     
             border:green dotted;border-width:0 2 2 0     
     }     
    
  </style>    
</head>    
<body>    
  <h1>Just a blank page.</h1>    
  <div >    
      <div align="center">    
          <h1>${title}测测</h1>    
      </div>    
      <table>    
         <tr>    
            <td><b>Name</b></td>    
            <td><b>Age</b></td>    
            <td><b>Sex</b></td>    
         </tr>    
         <#list userList as user>    
            <tr>    
                <td>${user.name}</td>    
                <td>${user.age}</td>    
                <td>    
                   <#if user.sex = 1>    
                         male     
                   <#else>    
                         female     
                   </#if>    
                </td>    
            </tr>    
         </#list>    
      </table>    
  </div>    
</body>    
</html>    