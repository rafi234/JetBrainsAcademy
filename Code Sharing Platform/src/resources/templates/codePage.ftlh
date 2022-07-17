<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Code</title>
  <link rel="stylesheet" href="/css/codePage-style.css">
  <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
  <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
  <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#if code??>
  <span id="load_date">${code.getFormattedDate()!}</span>
  <#if code.viewsDependent>
    <span id="views_restriction">${code.views!} more views allowed</span>
  </#if>
  <#if code.timeDependent>
    <span id="time_restriction">The code will be available for ${code.timeLeft!} seconds</span>
  </#if>
  <pre id="code_snippet"><code>${code.code!}</code></pre>
</#if>
</body>
