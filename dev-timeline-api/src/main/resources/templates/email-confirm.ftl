<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>dev-time 이메일 인증</title>
</head>
<body>
<input type="hidden" value="${message}" />
<script>
  var message = "${message?js_string}";
  alert(message);
  history.back();
</script>
</body>
</html>