import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/textClass.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String receivedText = "";
  static const platformChannel =
      MethodChannel('com.example.myandroid/text_channel');
  TextFromFlutter textflutter = TextFromFlutter();

  @override
  void initState() {
    super.initState();

    platformChannel.setMethodCallHandler((call) async {
      print("Method call received: ${call}");
      if (call.method == 'setText') {
        print("Received text: ${call.arguments}");
        print("Received text: ${call.arguments.toString()}");
        setState(() {
          receivedText = call.arguments.toString();
          textflutter.message = call.arguments;
        });
        print('===================$receivedText================');
      }
    });
  }

  void consoleLog() {
    print("Loging : $receivedText");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(textflutter.message),
            ElevatedButton(onPressed: consoleLog, child: Text('log')),
          ],
        ),
      ),
    );
  }
}
