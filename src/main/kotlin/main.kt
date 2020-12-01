import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import java.lang.Exception
import javax.swing.SwingUtilities.invokeLater

/*
* Взято отсюда
* https://dev.to/tkuenneth/from-swing-to-jetpack-compose-desktop-1-12kf?utm_campaign=jetc.dev%20Newsletter&utm_medium=email&utm_source=Revue%20newsletter
    2 часть
  https://dev.to/tkuenneth/from-swing-to-jetpack-compose-desktop-2-4a4h
* */

fun main() {
    invokeLater {
        AppWindow(title = "Еще пример окошка", size = IntSize(800, 600)).show {
            TKDupeFinderContent()
        }
    }
}


@Composable
fun TKDupeFinderContent() {
    val name = remember {
        mutableStateOf(TextFieldValue(""))
    }
    DesktopMaterialTheme {
        Column() {
            FirstRow()
            SecondRow()
            ThirdRow()
        }
    }


    val target = object : DropTarget() {
        @Synchronized
        override fun drop(evt: DropTargetDropEvent) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_REFERENCE)
                val droppedFiles = evt.transferable.getTransferData(
                    DataFlavor.javaFileListFlavor) as List<*>
                droppedFiles.first()?.let {
                    name.value = TextFieldValue((it as File).absolutePath)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
AppManager.windows.first().window.contentPane.dropTarget=target
}


@Composable
fun FirstRow() {
    val name = remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        TextField(
            value = name.value,
            placeholder = {
                Text("Основная директория:")
            },
            modifier = Modifier.alignBy(LastBaseline).weight(1.0f),
            onValueChange = {
                name.value = it
            },
        )
        MySpacer()
        Button(
            onClick = {},
            modifier = Modifier.alignByBaseline(),
            enabled = File(name.value.text).isDirectory
        ) {
            Text("Поиск")
        }
    }
}

@Composable
fun SecondRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(onClick = {}) {
            Text("\u140A")
        }
        MySpacer()
        Button(onClick = {}) {
            Text("\u1405")
        }
        MySpacer()
        Text(text = "1 из 42")
    }
}

@Composable
fun MySpacer() {
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun ThirdRow() {
    val scrollState = rememberScrollState()
    ScrollableColumn(
        scrollState = scrollState,
        modifier = Modifier.fillMaxSize().padding(8.dp),
    ) {
        Text("1")
        Text("2")
        Text("3")
    }
}