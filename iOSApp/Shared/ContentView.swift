//
//  ContentView.swift
//  Shared
//
//  Created by Omar Mainegra on 7/27/22.
//

import SwiftUI
import common_core
import Combine
import KMPNativeCoroutinesCombine

struct ContentView: View {
    
    @StateObject var whiteboard = Whiteboard()
    
    @State var startedDragging = false
    
    var body: some View {
        VStack {
            Canvas { context, size in
                whiteboard.state.segments.forEach { segment in
                    if (segment.points.count >= 2) {
                        let head = segment.points[0]
                        let tail = segment.points.dropFirst()
                        
                        var path = Path()
                        path.move(to: CGPoint(x: CGFloat(head.x), y: CGFloat(head.y)))
                        
                        for point in tail {
                            path.addLine(to: CGPoint(x: CGFloat(point.x), y: CGFloat(point.y)))
                        }
                        
                        context.stroke(path, with: .color(segment.color.toSwift()), lineWidth: CGFloat(segment.width))
                    }
                }
            }.gesture(
                DragGesture(coordinateSpace:.local)
                    .onChanged { value in
                        if startedDragging {
                            whiteboard.onMoved(Float(value.location.x), Float(value.location.y))
                            
                        } else {
                            startedDragging = true
                            whiteboard.onPress(Float(value.location.x), Float(value.location.y))
                        }
                    }
                    .onEnded { value in
                        whiteboard.onRelease(Float(value.location.x), Float(value.location.y))
                        startedDragging = false
                    }
            )
            
            HStack {
                ColorChooserView()
                StrokeWidthChooserView()
            }
        }.environmentObject(whiteboard)
    }
}

class Whiteboard : ObservableObject {
    
    @Published var state: DrawingState = DrawingStateHolderIoS.companion.InitialDrawingState
    
    private let stateHolder = DrawingStateHolderIoS()    
    private var cancellableSet = Set<AnyCancellable>()
    
    init() {
        createPublisher(for: stateHolder.stateNative)
            .assertNoFailure()
            .receive(on: DispatchQueue.main)
            .assign(to: \.state, on: self)
            .store(in: &cancellableSet)
    }
    
    func onPress(_ x: Float, _ y: Float) {
        stateHolder.onEvent(event: DrawingEvent.MotionEventPressed(x: x, y: y))
    }
    
    func onMoved(_ x: Float, _ y: Float) {
        stateHolder.onEvent(event: DrawingEvent.MotionEventMoved(x: x, y: y))
    }
    
    func onRelease(_ x: Float, _ y: Float) {
        stateHolder.onEvent(event: DrawingEvent.MotionEventReleased(x: x, y: y))
    }
    
    func onColorChosen(_ color: SwiftUI.Color) {
        stateHolder.onEvent(event: DrawingEvent.ColorChanged(value: color.toCommon()))
    }
    
    func onWidthSelected(_ value: Int) {
        stateHolder.onEvent(event: DrawingEvent.StrokeWidthChanged(value: Int32(value)))
    }
}

struct ColorChooserView: View {
    
    @EnvironmentObject var whiteboard: Whiteboard
    
    var body: some View {
        HStack {
            ForEach(whiteboard.state.colorOptions.map { $0.toSwift() }, id: \.self) { color in
                Button(action: {
                    whiteboard.onColorChosen(color)
                }, label: {
                    Rectangle()
                        .frame(width: 32, height: 32)
                        .foregroundColor(color)
                        .background(color)
                        .clipShape(Circle())
                })
            }
        }
    }
}

struct StrokeWidthChooserView: View {
    
    @EnvironmentObject var whiteboard: Whiteboard
    
    private let values: [Int] = [1, 5, 10, 15, 20]
    
    var body: some View {
        Menu("\(whiteboard.state.strokeWidth)") {
            ForEach(values, id: \.self) { width in
                Button("\(width)", action: { whiteboard.onWidthSelected(width) })
            }
        }
    }
}

extension SwiftUI.Color {
    func toCommon() -> common_core.Color {
        let components = UIColor(self).cgColor.components!
        
        let r = Int32(components[0]*255.0)
        let g = Int32(components[1]*255.0)
        let b = Int32(components[2]*255.0)
        
        return common_core.Color(r: r, g: g, b: b)
    }
}

extension common_core.Color {
    func toSwift() -> SwiftUI.Color {
        return SwiftUI.Color(.sRGB, red: Double(r)/255.0, green: Double(g)/255.0, blue: Double(b)/255.0, opacity: 1.0)
    }
}
